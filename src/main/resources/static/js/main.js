 function validateCaptcha() {
    const captcha = document.getElementById("captcha").value;
    const userCaptcha = document.getElementById("userCaptcha").value;
    if (captcha !== userCaptcha) {
      alert("Invalid Captcha! Please try again.");
      return false;
    }

    const fileInput = document.getElementById('file');
    const file = fileInput.files[0];
    if (file) {
        const fileSize = file.size;
        const maxSize = 1024 * 1024;

        if (fileSize > maxSize) {
            alert('File size need less than or 1 MB.');
            return false;
        }
    }
    return true;
}