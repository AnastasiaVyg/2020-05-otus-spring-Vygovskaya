FROM node:12-alpine as build
WORKDIR /app
COPY package.json /app/package.json
RUN npm install --only=prod
COPY . /app
RUN npm run build
FROM nginx:1.16.0-alpine
COPY --from=build /app/build /reactlibrary
COPY compose.nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 8000
CMD ["nginx", "-g", "daemon off;"]