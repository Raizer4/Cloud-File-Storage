FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install -g @angular/cli
RUN npm ci
COPY . .
ARG ENVIRONMENT
RUN ng build --configuration ${ENVIRONMENT}

FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]