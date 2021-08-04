module.exports = {
    module: {
      rules: [
        {
          test: /\.styl$/,
          use: [
            {
              loader: "style-loader", // creates style nodes from JS strings
            },
            {
              loader: "css-loader", // translates CSS into CommonJS
            },
            {
              loader: "stylus-loader", // compiles Stylus to CSS
            },
          ],

        },
        {
          test: /.js$/,
          loader: 'babel-loader',
          include: [
            resolve('src'),
            resolve('test'),
            resolve('node_modules/webpack-dev-server/client')
          ]
        },
      ],
    },
  };
