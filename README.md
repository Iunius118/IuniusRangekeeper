# Iunius's Rangekeeper

Minecraft 1.12.2用の射撃補助ツールを追加するmod。Forge 1.12.2-14.23.4.2705+ 対応。

ダウンロード：[Releases](https://github.com/Iunius118/IuniusRangekeeper/releases)

## レシピ

### Rangekeeper

i = 鉄インゴット、G = ガラス、P = ピストン、c = 時計

```text
iG
Pc
```

## 使用法

1. 事前にゲーム内のmod一覧から`Iunius's Rangekeeper`を選択し、`Config`画面を開いて`Ballistic Parameters`（使用する射撃武器と弾丸のパラメータ）の各項目を設定する
2. Rangekeeperをメインハンドに持って、モブやブロックを左クリックする
    - 対象に近すぎると反応しない（対象を殴ってしまう）ので、対象から6メートル以上離れて左クリックする
3. 画面に□と◇のマーカーが表示される
    - □マーカーは追尾しているモブやブロック
    - ◇マーカーは`Ballistic Parameters`と追尾対象の動きをもとに算出した見越し点。算出不能なときは表示されない
    - 各マーカーは一人称視点で、Rangekeeperをメインハンドまたはオフハンドに持っているときのみ表示される
4. 追尾を解除するときは空を左クリックするかスニーク状態で左クリックする
