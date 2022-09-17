# create-service

A utility tool to create and hydrate a [service-framework](https://github.com/ironman19933/service-framework) project.

To install, run: 

```shell
source <(curl -s https://raw.githubusercontent.com/hardikphalet/create-service/main/scripts/install.sh)
```

To create skeleton, run after installation:

```shell
sfc -generate package.name.here
cd ./skeleton
# Package must be triple layered
```

Create your entities in the entity package. To Hydrate project with classes, run:

```shell
sfc -hydrate
```