// 画面（DOM）の読み込みがすべて完了してから動かす
document.addEventListener('DOMContentLoaded', () => {
  
  // 1. 操作したいHTMLの要素を取得する
  const imageInput = document.getElementById('prototype_image');
  const imagePreview = document.getElementById('image-preview');
  const previewContainer = document.getElementById('image-preview-container');

  // 万が一、要素が取得できない画面の場合はエラー防止のため処理を中断する
  if (!imageInput || !imagePreview || !previewContainer) return;

  // 2. ファイル選択ボタンの「中身が変わった時（change）」のイベントを監視
  imageInput.addEventListener('change', (e) => {
    // 選択されたファイルリストから、最初の1枚を取得
    const file = e.target.files[0];

    if (file) {
      // ブラウザ上でファイルを読み込むためのオブジェクト（FileReader）を作成
      const reader = new FileReader();

      // ファイルの読み込みが「完了したとき」の動きを定義
      reader.onload = (e) => {
        // 読み込んだ画像データ（URL形式）を img タグの src にセット
        imagePreview.src = e.target.result;
        // プレビューエリアを表示状態（display: block）にする
        previewContainer.style.display = 'block';
      };

      // 実際にファイルをデータURL（base64）として読み込む
      reader.readAsDataURL(file);
    } else {
      // ファイルの選択がキャンセル（空）された場合は、プレビューを非表示に戻す
      previewContainer.style.display = 'none';
      imagePreview.src = '';
    }
  });
});