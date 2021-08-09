package com.example.exif.model


class Image {
    var imageId: String? = null
    var albumId: String? = null
    var imagePath: String? = null
    var imageName: String? = null
    var imageSize: Long? = null
    var imageSentence1: String? = null
    var imageSentence2: String? = null
    var imageSentence3: String? = null

    constructor(imagePath: String?, imageName: String?) {
        this.imagePath = imagePath
        this.imageName = imageName
    }

    constructor() {}
}