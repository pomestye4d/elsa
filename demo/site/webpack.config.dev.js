const path = require('path');
// eslint-disable-next-line import/order
const merge = require('webpack-merge');
const commonConfig = require('./webpack.config.common');

module.exports = merge.merge(commonConfig, {
  mode: 'development',
  devtool: 'inline-source-map',
  devServer: {
    static: {
      directory: path.join(__dirname, 'build'),
    },
    proxy: {
      '/remoting': 'http://localhost:8086',
    },
    compress: false,
    port: 3000,
  },
});
