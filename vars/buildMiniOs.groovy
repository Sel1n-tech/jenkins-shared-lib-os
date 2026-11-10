// vars/buildMiniOs.groovy
import common.OSFactory
import common.products.BaseOS

def call(Map config) {
    // Создаем объект ОС через фабрику
    BaseOS os = OSFactory.createOS(config.osType, config)
    
    // Валидация
    os.validate()
    
    echo "🚀 =========================================="
    echo "🚀 Начинаем Docker-сборку: ${os.osType}"
    echo "📌 Тэг образа: ${os.fullImageName}"
    echo "📝 Описание: ${os.description}"
    echo "📦 Базовый образ: ${os.baseImage}"
    echo "🚀 =========================================="
    
    stage("Build Docker Image: ${os.osType}") {
        sh "docker build --build-arg BASE_IMAGE=${os.baseImage} -t ${os.fullImageName} ."
        
        echo "⏳ Имитация дополнительных тестов образа (${os.buildTime} сек)..."
        sh "sleep ${os.buildTime}"
    }

    stage("Tag and Push (Simulation)") {
        echo "🏷️  Тэгирование образа для реестра..."
        sh "docker images | grep ${os.imageName}"
        echo "✅ Образ ${os.fullImageName} успешно собран и готов к отправке в registry!"
    }
    
    // Возвращаем объект для дальнейшего использования (например, в оркестраторе)
    return os
}