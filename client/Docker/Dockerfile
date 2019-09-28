# build environment
FROM node:12.2.0-alpine as build
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app/package.json
RUN npm install react-scripts@3.1.1 -g
COPY . /app
RUN npm install --prefix /app
RUN npm run build --prefix /app

# production environment
FROM nginx:1.16.0-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]