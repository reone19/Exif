# Mstorage

Android写真アプリ＋α

# DEMO

![画面遷移](https://user-images.githubusercontent.com/57529474/131344009-896b8d87-c8f8-4695-8660-f1d7be76272a.gif)
![キャプション・Exif・OCR](https://user-images.githubusercontent.com/57529474/131344503-5e8f5d00-fad0-4c18-9843-9cf7f8e6a8c3.gif)

# Features

* Exif情報登録や編集、OCRによる文字列抽出、キャプションに任意の検索ワードを付与することによって、大量の画像の中から特定の情報を持った画像を簡単に探し出すことができます。

* 画像に付随しているExifデータを一括で削除することも可能なため、インターネット上に画像をアップロードする際、画像のExifデータから個人情報を特定されるなどのリスクを回避できます。

* スマホの写真アプリでは確認することが難しい、画像ファイル名やファイルサイズを確認できます。

# Usage

1. git clone [https://github.com/reone19/Exif.git](https://github.com/reone19/Exif.git)
2. android studioで開く
3. エミュレーター又はandroid実機にて実行

# Note

* ダークモードに対応しています。

* 画像の横スライドに対応しています。

* API27の場合、アプリ初回起動時に落ちる場合があります。

* 初回起動時に表示される画面（権限）について<br>
  API30未満の場合、ポップアップ表示された権限を許可することで、外部ストレージの読み書きが可能です。<br>
  API30の場合、ポップアップ表示された権限で外部ストレージの読み取り権限を許可し、その後出現する画面で書き込み権限の許可を行います。<br>
  書き込み権限は画像のExifデータを直接編集する場合に使用されます。<br>
  API30からExif編集機能利用にアクセス許可が必要になりました。

* 文字のない画像や解像度の悪い画像にOCRを使うと処理に時間がかかります。

# Author

* [daiki-m-hal](https://github.com/daiki-m-hal)
* [reone19](https://github.com/reone19)
* [nagi-lc3](https://github.com/nagi-lc3)
* [nakaosan](https://github.com/nakaosan)
* [misoshi](https://github.com/misoshi)
