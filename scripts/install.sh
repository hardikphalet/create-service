# First check OS.
OS="$(uname)"

abort() {
  printf "%s\n" "$@" >&2
  exit 1
}

if [[ "${OS}" != "Darwin" ]]
  then
    SFC_ON_UNSUPPORTED=1
    abort "sfc is only supported on MacOS."
fi

if [[ -z "${SFC_ON_UNSUPPORTED-}" ]]
  then
    UNAME_MACHINE="$(/usr/bin/uname -m)"

    if [[ "${UNAME_MACHINE}" == "arm64" ]]
      then
        SFC_PREFIX="/opt/sfc"
        SFC_REPOSITORY="${SFC_PREFIX}"
      else
        SFC_PREFIX="/usr/local"
        SFC_REPOSITORY="${SFC_PREFIX}/sfc"
    fi
fi

SFC_DEFAULT_GIT_REMOTE="https://github.com/hardikphalet/create-service.git"
check_java_version() {
  if type -p java; then
      echo found java executable in PATH
      _java=java
  elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
      echo found java executable in JAVA_HOME
      _java="$JAVA_HOME/bin/java"
  else
      abort "no java"
  fi

  if [[ "$_java" ]]; then
      version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
      echo java version found: version "$version"
      if [[ "$(bc <<< "${version:0:2} == 11")" == 1 ]]; then
          echo version is 11
      else
          abort version is not 11
      fi
  fi
}

check_java_version

shell_join() {
  local arg
  printf "%s" "$1"
  shift
  for arg in "$@"
  do
    printf " "
    printf "%s" "${arg// /\ }"
  done
}

execute() {
  if ! "$@"
  then
    abort "$(printf "Failed during: %s" "$(shell_join "$@")")"
  fi
}

execute "git" "clone" "${SFC_DEFAULT_GIT_REMOTE}"
cd ./create-service || exit
execute "./gradlew" "assemble"
echo "$PATH:$(pwd)"
export PATH="$PATH:$(pwd)"
echo export PATH="\$PATH:$(pwd)" >> ~/.zshrc
echo export SFC_HOME="$(pwd)" >> ~/.zshrc
cd ..