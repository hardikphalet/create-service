# create-service

A utility tool to create and hydrate a [service-framework](https://github.com/ironman19933/service-framework) project.

## Installation

To install, run: 

```shell
source <(curl -s https://raw.githubusercontent.com/hardikphalet/create-service/main/scripts/install.sh)
```

## Usage

To create skeleton, run after installation:

```shell
sfc -generate package.name.here
cd ./skeleton
```

Create your entities in the entity package. To Hydrate project with classes, run:

```shell
sfc -hydrate
```

## Uninstallation

Delete ```service-framework``` directory. Remove the following lines from ```~/.zshrc```.
```shell
export PATH="$PATH:<Path to previous installation>"
export SFC_HOME="<Path to previous installation>"
```