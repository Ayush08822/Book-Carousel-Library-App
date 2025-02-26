import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import fs from 'fs';

// Path to your certificates
const sslDir = './ssl-localhost';

export default defineConfig({
  server: {
    https: {
      key: fs.readFileSync(`${sslDir}/localhost.key`),
      cert: fs.readFileSync(`${sslDir}/localhost.crt`),
    },
  },
  plugins: [react()],
});
