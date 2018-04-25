# Jenkins Development Environment

Docker compose will bring 3 containers:
 - jenkins master
 - jenkins slave connected with jnlp and docker ready
 - influxdb (not configured)

## Run with

```
docker-compose up
```

## Tweak config

Have a look at the `docker-compose.yml`. Environment variables are stored there