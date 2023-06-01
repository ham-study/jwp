// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);
$(".answerDelete button").click(deleteAnswer);

const articles = document.querySelector(".qna-comment-slipp-articles");

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccessAdd,
  });
}

function onSuccessAdd(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

function deleteAnswer(e) {
  e.preventDefault();
  const queryString = $(e.target.form).serialize();
  const answerId = $(e.target.form).context[0].defaultValue;

  const article = articles.querySelector(`article[id='${answerId}']`);

  $.ajax({
    type : 'post',
    url : '/api/qna/deleteAnswer',
    data: queryString,
    dataType : 'json',
    error: onError,
    success : () => articles.removeChild(article),
  });
}