module.exports = {
    devServer: {
        proxy: {
            '/fleamarket' : {
                target: 'http://localhost:8081',
            }
        },
    }
}