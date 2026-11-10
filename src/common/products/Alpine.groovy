// src/common/products/Alpine.groovy
package common.products

class Alpine extends BaseOS implements Serializable {
    
    // Конструктор (можно передавать параметры при создании)
    Alpine(Map config = [:]) {
        this.osType = config.osType ?: 'alpine-3.18'
        this.releaseVersion = config.releaseVersion ?: '1.0.0-beta'
        this.imageName = config.imageName ?: 'my-mini-os'
        this.description = 'Легковесная сборка Alpine Linux'
    }
    
    // Реализация абстрактных методов
    @Override
    String getBaseImage() {
        return 'alpine:3.18'
    }
    
    @Override
    int getBuildTime() {
        return 2 // Alpine быстрый
    }
    
    @Override
    Map getTestConfig(String releaseVersion) {
        def tests = [
            '1.0.0-beta': [testSuite: 'smoke-tests', timeout: 5],
            '1.0.0-rc':   [testSuite: 'security-scan', timeout: 15],
            '1.0.0-stable': [testSuite: 'performance-tests', timeout: 30]
        ]
        return tests[releaseVersion] ?: [testSuite: 'default-tests', timeout: 10]
    }
    
    // Специфичный для Alpine метод
    String getPackageManager() {
        return 'apk'
    }
}