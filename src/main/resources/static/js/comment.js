document.addEventListener('DOMContentLoaded', () => {
  const commentInput = document.getElementById('comment_input');
  const charCount = document.getElementById('char_count');
  const counterContainer = document.querySelector('.counter-container');
  const commentSubmit = document.getElementById('comment_submit');

  // 要素が足りない場合はエラー防止のため処理をスキップ
  if (!commentInput || !charCount || !counterContainer || !commentSubmit) return;

  const MAX_CHARS = 140;

  // 入力欄に文字が入力されるたびに（inputイベント）発火する処理
  commentInput.addEventListener('input', () => {
    // 現在入力されている文字数を取得
    const currentLength = commentInput.value.length;

    // 画面の数字を書き換える
    charCount.textContent = currentLength;

    // 1. 文字数が0、または140文字を超えた場合はボタンを押せなくする（disabled）
    if (currentLength === 0 || currentLength > MAX_CHARS) {
      commentSubmit.disabled = true;
      commentSubmit.style.opacity = '0.5'; // ボタンを半透明にして無効感を出す
      commentSubmit.style.cursor = 'not-allowed';
    } else {
      commentSubmit.disabled = false;
      commentSubmit.style.opacity = '1';
      commentSubmit.style.cursor = 'pointer';
    }

    // 2. 文字数が上限（140文字）に達したら文字色を赤くする
    if (currentLength >= MAX_CHARS) {
      counterContainer.classList.add('danger');
    } else {
      counterContainer.classList.remove('danger');
    }
  });

  // 💡 初期状態（画面を開いた瞬間）は0文字なのでボタンを無効化しておく
  commentSubmit.disabled = true;
  commentSubmit.style.opacity = '0.5';
  commentSubmit.style.cursor = 'not-allowed';
});