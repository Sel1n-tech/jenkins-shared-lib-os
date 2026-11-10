// src/common/products/Ubuntu.groovy
package common.products

class Ubuntu extends BaseOS implements Serializable {
    
    Ubuntu(Map config = [:]) {
        this.osType = config.osType ?: 'ubuntu-24.04'
        this.releaseVersion = config.releaseVersion ?: '1.0.0-beta'
        this.imageName = config.imageName ?: 'my-mini-os'
        this.description = 'Стандартная сборка Ubuntu Server'
    }
    
    @Override
    String getBaseImage() {
        return 'ubuntu:24.04'
    }
    
    @Override
    int getBuildTime() {
        return 5 // Ubuntu медленнее
    }
    
    @Override
    Map getTestConfig(String releaseVersion) {
        def tests = [
            '1.0.0-beta': [testSuite: 'smoke-tests', timeout: 10],
            '1.0.0-rc':   [testSuite: 'integration-tests', timeout: 20],
            '1.0.0-stable': [testSuite: 'full-regression', timeout: 60]
        ]
        return tests[releaseVersion] ?: [testSuite: 'default-tests', timeout: 15]
    }
    
    // Специфичный для Ubuntu метод
    String getPackageManager() {
        return 'apt'
    }
}