## Docker Compose

#### Single Docker-Compose service "up"

```bash
docker-compose -f docker-compose-production-build.yml up -d --build request-bin-server
```

#### Single Docker-Compose service "down"

```bash
docker-compose -f docker-compose-production-build.yml rm -f -s -v request-bin-server
```
About:  
```bash
Usage: rm [options] [SERVICE...]

Options:
-f, --force Don't ask to confirm removal
-s, --stop Stop the containers, if required, before removing
-v Remove any anonymous volumes attached to containers
```

#### View Docker container environment

```bash
docker exec request-bin-server env
```