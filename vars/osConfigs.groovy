// vars/osConfigs.groovy

// Возвращает конфигурации для сборки (используется в Job 1)
def getBuildConfig(String osType) {
    def builds = [
        'ubuntu-24.04': [ baseImage: 'ubuntu:24.04', buildTime: 5 ],
        'alpine-3.18': [ baseImage: 'alpine:3.18', buildTime: 2 ]
    ]
    return builds[osType]
}

// Возвращает конфигурации для тестов (используется в Job 2 -> Job 3)
def getTestConfig(String osType, String releaseVersion) {
    def tests = [
        'ubuntu-24.04': [
            '1.0.0-beta': [testSuite: 'smoke-tests', timeout: 10],
            '1.0.0-rc':   [testSuite: 'integration-tests', timeout: 20],
            '1.0.0-stable': [testSuite: 'full-regression', timeout: 60]
        ],
        'alpine-3.18': [
            '1.0.0-beta': [testSuite: 'smoke-tests', timeout: 5],
            '1.0.0-rc':   [testSuite: 'security-scan', timeout: 15],
            '1.0.0-stable': [testSuite: 'performance-tests', timeout: 30]
        ]
    ]
    
    // Защита от отсутствия ключа
    if (!tests.containsKey(osType) || !tests[osType].containsKey(releaseVersion)) {
        error("❌ Нет конфигурации тестов для ${osType} версии ${releaseVersion}")
    }
    
    return tests[osType][releaseVersion]
}