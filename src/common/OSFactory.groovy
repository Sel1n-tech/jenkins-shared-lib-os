// src/common/OSFactory.groovy
package common

import common.products.*

class OSFactory implements Serializable {
    
    // Статический метод для создания нужного объекта по типу
    static BaseOS createOS(String osType, Map config = [:]) {
        config.osType = osType
        
        switch (osType) {
            case 'alpine-3.18':
                return new Alpine(config)
            case 'ubuntu-24.04':
                return new Ubuntu(config)
            default:
                throw new IllegalArgumentException("❌ Unknown OS type: ${osType}")
        }
    }
    
    // Метод для получения списка доступных ОС
    static List<String> getAvailableOSTypes() {
        return ['alpine-3.18', 'ubuntu-24.04']
    }
}