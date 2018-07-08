ゆづことMOD
===

数種類のアイテムやエンチャントなどを追加する
VOICEROIDをモチーフとしたMinecraftのMODです。

---

## 概要
ニコニコ動画で「結月さんと琴葉ちゃんのForgeであそぶ！」シリーズでMod開発の解説に使用していたプログラムです。  
試行錯誤しながら作成していたため、不要な処理なども含まれていますが動画で解説した処理については、基本的にすべて含まれています。

## このMODについて
Releaseで公開しているMODは「結月さんと琴葉ちゃんのForgeであそぶ！」で解説や紹介を行ったものが含まれています。
- part02 紫ダイヤモンド
- part03 ゆづことブロック
- part06 紫うさぎ
- part07 にわとりの加護、グレートエレキファイアーのエンチャント
- part10 結月の祭壇、琴葉のツルハシ、紲星のクワ
- メイドさんごっこ
- 福箱
- 古い剣

詳しい内容についてはこちらをご覧下さい。
[本編](http://www.nicovideo.jp/mylist/60984825)
[短編集](http://www.nicovideo.jp/mylist/60984874)


## 注意事項
Minecraft 1.11.2  
Minecraft Forge 1.11.2-13.20.1.2386  
  
サバイバル、シングルのみ動作確認しているためそれ以外では動作しない可能性があります。  
自己責任にてご使用ください。


## 構築手順（Windows/Eclipse）
1. リポジトリをローカルへ展開（ダウンロード or Git）
1. コマンドラインで展開した場所へ
1. gradlew setupDecompWorkspace
1. gradlew eclipse
1. 失敗したら gradlew clean 後にやり直す
1. Eclipseへインポート
1. 実行構成「Java アプリケーション -> プロジェクト名_Client」を設定すればEclipse上で実行できる
1. gradlew build でbuild/lib下にjarファイルができあがる