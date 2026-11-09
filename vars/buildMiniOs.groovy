// vars/buildMiniOs.groovy

// Функция 'call' является точкой входа. Она принимает Map (словарь) с параметрами.
def call(Map config) {
    
    // 1. ОПРЕДЕЛЯЕМ MAP С КОНФИГУРАЦИЯМИ
    // В реальном проекте это может загружаться из внешнего JSON/YAML файла или Vault
    def osConfigs = [
        'ubuntu-24.04': [
            baseImage: 'ubuntu:24.04',
            buildScript: 'scripts/build_ubuntu.sh',
            credentialsId: 'fake-ubuntu-registry-creds',
            description: 'Стандартная сборка Ubuntu Server'
        ],
        'alpine-3.18': [
            baseImage: 'alpine:3.18',
            buildScript: 'scripts/build_alpine.sh',
            credentialsId: 'fake-alpine-registry-creds',
            description: 'Легковесная сборка Alpine Linux'
        ]
    ]

    // 2. ВАЛИДАЦИЯ ВХОДНЫХ ДАННЫХ
    def selectedOs = config.osType
    if (!osConfigs.containsKey(selectedOs)) {
        // Если передана неизвестная ОС, мы красиво прерываем пайплайн с ошибкой
        error("❌ Неизвестная ОС: '${selectedOs}'. Доступные варианты: ${osConfigs.keySet().join(', ')}")
    }

    // Получаем конкретную конфигурацию из Map по ключу
    def buildInfo = osConfigs[selectedOs]

    // 3. ВЫПОЛНЕНИЕ ЛОГИКИ СБОРКИ
    echo "🚀 =========================================="
    echo "🚀 Начинаем сборку мини-ОС: ${selectedOs}"
    echo "📌 Версия релиза: ${config.releaseVersion}"
    echo "📝 Описание: ${buildInfo.description}"
    echo "🔑 Используем креды: ${buildInfo.credentialsId}"
    echo "🚀 =========================================="
    
    stage('Prepare Environment') {
        echo "📦 Имитация загрузки базового образа: ${buildInfo.baseImage}"
        sh 'sleep 2' // Имитация сетевой задержки
    }
    
    stage('Build Process') {
        echo "🛠️ Запуск скрипта сборки: ${buildInfo.buildScript}"
        sh "echo 'Компиляция ядра и пакетов для ${selectedOs}...'"
        sh "echo 'Версия артефакта: ${config.releaseVersion}'"
        sh 'sleep 3' // Имитация долгой компиляции
    }

    echo "✅ Сборка ${selectedOs} (версия ${config.releaseVersion}) успешно завершена!"
}