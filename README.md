# Mstorage

Androidの写真アプリ＋α

# DEMO

"Mstorage"の魅力が直感的に伝えわるデモ動画や図解を載せる

# Features

Exif情報登録や編集、OCRによる文字列抽出、キャプションに任意の文字列を付与することによって
大量の写真の中から特定の情報を持った写真を仕分けし、検索することができるため
特定条件で写真を管理しやすくできる。

写真に登録してあるExif情報を一括で削除することも可能なため
インターネット上に写真をアップロードする際、写真のExifデータから
個人情報を特定されるなどのリスクを回避することができる。

# Usage

1. git clone [https://github.com/reone19/Exif.git](https://github.com/reone19/Exif.git)
1. android studioで開く
1. エミュレータ又はandroid実機にて実行

# Note

* API27の場合、初回起動時にアプリが落ちる場合があります
* API30の場合、初回起動時に表示される画面について
*  →API30の場合、初回起動時に表示されるアラートは計2つあります。
*  一つは外部ストレージへのアクセス許可ともう片方はExif編集権限のアクセス許可です。
*  API30からExif編集機能利用にアクセス許可が必要になりました。
* 文字のない画像にOCRを使うと処理に時間がかかります
* 解像度の悪い画像にOCRを使うと処理に時間がかかります

# Technology used
画像表示

# Author

* [daiki-m-hal](https://github.com/daiki-m-hal)
* [reone19](https://github.com/reone19)
* [nagi-lc3](https://github.com/nagi-lc3)
* [nakaosan](https://github.com/nakaosan)
* [misoshi](https://github.com/misoshi)
