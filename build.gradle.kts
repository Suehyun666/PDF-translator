plugins {
    id("java")
}

group = "project"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.apache.pdfbox:pdfbox:2.0.27")

    // Tesseract OCR 라이브러리
    implementation ("net.sourceforge.tess4j:tess4j:4.5.1")

    // JSON 처리용 라이브러리 (DeepL API 사용 시 필요)
    implementation ("org.json:json:20210307")

    // 스윙 GUI 관련
    implementation ("org.springframework:spring-core:5.3.18")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

}

tasks.test {
    useJUnitPlatform()
}