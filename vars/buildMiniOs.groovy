// vars/buildMiniOs.groovy

def call(Map config) {
    def osConfigs = [
        'ubuntu-24.04': [
            baseImage: 'ubuntu:24.04',
            description: 'Стандартная сборка Ubuntu Server',
            buildTime: 5 // Имитация более долгой сборки для Ubuntu
        ],
        'alpine-3.18': [
            baseImage: 'alpine:3.18',
            description: 'Легковесная сборка Alpine Linux',
            buildTime: 2 // Alpine собирается быстрее
        ]
    ]

    def selectedOs = config.osType
    if (!osConfigs.containsKey(selectedOs)) {
        error("❌ Неизвестная ОС: '${selectedOs}'. Доступные: ${osConfigs.keySet().join(', ')}")
    }

    def buildInfo = osConfigs[selectedOs]
    def fullImageName = "${config.imageName}:${config.releaseVersion}-${selectedOs}"

    echo "🚀 =========================================="
    echo "🚀 Начинаем Docker-сборку: ${selectedOs}"
    echo "📌 Тэг образа: ${fullImageName}"
    echo "📝 Описание: ${buildInfo.description}"
    echo "🚀 =========================================="
    
    stage("Build Docker Image: ${selectedOs}") {
        // Реальная команда сборки Docker!
        // --build-arg передает переменную внутрь Dockerfile
        sh "docker build --build-arg BASE_IMAGE=${buildInfo.baseImage} -t ${fullImageName} ."
        
        echo "⏳ Имитация дополнительных тестов образа (${buildInfo.buildTime} сек)..."
        sh "sleep ${buildInfo.buildTime}"
    }

    stage("Tag and Push (Simulation)") {
        echo "🏷️  Тэгирование образа для реестра..."
        // Имитация push (в реальности здесь был бы docker push и использование с credentials)
        sh "docker images | grep ${config.imageName}"
        echo "✅ Образ ${fullImageName} успешно собран и готов к отправке в registry!"
    }
}