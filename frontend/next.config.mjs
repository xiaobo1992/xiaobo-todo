const backendUrl = process.env.BACKEND_URL || "http://localhost:8080";

/** @type {import('next').NextConfig} */
const nextConfig = {
  output: "standalone",
  // Proxy /api/* to the Micronaut backend so the browser never needs CORS.
  async rewrites() {
    return [{ source: "/api/:path*", destination: `${backendUrl}/:path*` }];
  },
};

export default nextConfig;
