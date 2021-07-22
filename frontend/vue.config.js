module.exports = {
    runtimeCompiler: true,
    devServer: {
        proxy: {
            '/fleamarket' : {
                target: 'http://localhost:8081',
            }
        },
    }
}