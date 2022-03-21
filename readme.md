#AndroidStudio 发布Module到JitPack
>经过几天的研究，AndroidStudio发布Module到JitPack，全程在一个Android Studio操作就行，无需网页点击发行版本，无需打开github.com/gitee.com/jitpack.io

**前提：已有github/gitee和jitpack账号，并已授权jitpack可访问github/gitee仓库，在此	不做描述**

[https://gitee.com/](https://gitee.com)

[https://github.com/](https://github.com/)

[https://jitpack.io/](https://jitpack.io/)

1. 打开AS，新建一个项目并新建一个Module，随便写一个工具类，然后推送到github/gitee
如下图所示：

![如图所示](https://tva1.sinaimg.cn/large/e6c9d24ely1h0e07hcvu7j21h80f6jua.jpg)

2. 当Android项目推送到github之后，就可以通过maven-publish插件，推送aar文件到[jitpack](https://jitpack.io)。maven-publish需要配置在准备发布的lib的build.gradle文件中。
如下图所示：

![如图所示](https://tva1.sinaimg.cn/large/e6c9d24ely1h0e0hc22ipj218h0sewit.jpg)
具体配置文件如下
```
plugins {
    id 'com.android.library'
    id 'kotlin-android'
    id 'maven-publish'//用来推送aar到jitpack
    id 'pl.allegro.tech.build.axion-release' version '1.13.6'//用来发行aar版本的插件
}
def properties = new Properties()
properties.load(rootProject.file("local.properties").newInputStream())
def gitUser = properties.getProperty("gitUser")
def gitPassWord = properties.getProperty("gitPassWord")
afterEvaluate {
    publishing {
        publications {
            release(MavenPublication) {
                from components.release
                groupId = 'com.gitee.xiaweifeng'//插件id，格式：com.gitee/github.用户名
                artifactId = 'JitPackTest11'//插件名称
                version = '1.0'//版本号
                //引用使用格式：implementation 'com.gitee.xiaweifeng:JitPackTest11:1.0'
            }
        }
        //jitpack推送授权
        repositories {
            maven {
                name('jitpack')
                url "https://jitpack.io"
                credentials {
                    username gitUser
                    password gitPassWord
                }
            }
        }

    }
}
//release插件，https授权
scmVersion{
    repository{
        customUsername = gitUser
        customPassword = gitPassWord
    }
}

android {
    compileSdkVersion 32

    defaultConfig {
        minSdkVersion 19
        targetSdkVersion 32
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {

    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.2.0'
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'com.google.android.material:material:1.1.0'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}
```

3. 接下来只需要执行几步Gradle task任务即可，buildaar文件，并上传到jitpack上
	![如图所示](https://tva1.sinaimg.cn/large/e6c9d24ely1h0e0ohvupkj211o0u0jxd.jpg)
4. 使用发行后的Module库，只需要在项目下的build.gradle文件下配置，maven仓库，再module下的build.gradle文件下配置
仓库id
	![如图所示](https://tva1.sinaimg.cn/large/e6c9d24ely1h0e0trbvp7j20sh0mlq4i.jpg)
	![如图所示](https://tva1.sinaimg.cn/large/e6c9d24ely1h0e0ux716wj20tx0e7q4f.jpg)

[参考链接](https://blog.csdn.net/qq594030472/article/details/113627937)
[参考链接](https://blog.csdn.net/qq_41885673/article/details/121588094)

