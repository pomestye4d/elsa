module.exports = {
  eslint: {
    enable: false,
  },
  devServer: {
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
};
