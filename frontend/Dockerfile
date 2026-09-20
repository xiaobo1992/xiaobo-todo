ARG REGISTRY=docker.m.daocloud.io/library
FROM ${REGISTRY}/node:22-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
ARG BACKEND_URL=http://backend:8080
ENV BACKEND_URL=$BACKEND_URL
RUN npm run build

FROM ${REGISTRY}/node:22-alpine
WORKDIR /app
ENV NODE_ENV=production
COPY --from=build /app/.next/standalone ./
COPY --from=build /app/.next/static ./.next/static
EXPOSE 3000
CMD ["node", "server.js"]
