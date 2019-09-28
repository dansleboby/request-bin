# STAGE 0
FROM node:12.2.0-alpine as build
LABEL stage=build-stage
RUN mkdir /app
WORKDIR /app
COPY ./package*.json /app/
RUN npm install
COPY ./ /app/
RUN npm run build

# STAGE 1
FROM nginx:alpine AS release
COPY --from=build /app/build/ /usr/share/nginx/html/
EXPOSE 80