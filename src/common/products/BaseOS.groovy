// src/common/products/BaseOS.groovy
package common.products

// Базовый класс с общими свойствами для всех ОС
abstract class BaseOS implements Serializable {
    
    // Общие поля (наследуются всеми дистрибутивами)
    String osType
    String releaseVersion
    String imageName
    String description
    
    // Абстрактные методы (каждый дистрибутив должен их реализовать)
    abstract String getBaseImage()
    abstract int getBuildTime()
    abstract Map getTestConfig(String releaseVersion)
    
    // Общий метод для всех (может быть переопределен)
    String getFullImageName() {
        return "${imageName}:${releaseVersion}-${osType}"
    }
    
    // Метод для валидации
    void validate() {
        if (!osType || !releaseVersion || !imageName) {
            throw new IllegalArgumentException("❌ Missing required fields: osType, releaseVersion, imageName")
        }
    }
}