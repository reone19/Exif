package com.example.exif.model

class Image {
    var imageid:String?=null
    var imagePath:String?=null
    var imageName:String?=null

    constructor(imagePath: String?, imageName: String?){
        this.imagePath = imagePath
        this.imageName = imageName
    }
    constructor()
    {}
}