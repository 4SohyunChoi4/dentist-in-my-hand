const request = require('request');
const options = {
    uri:'https://fcm.googleapis.com/fcm/send',
    method:'POST',
    headers: {
        "content-type" : "application/json",
        "Authorization": "key=AAAA1jfo9tA:APA91bEH7WtpKPKpatm4ZZo8-sDvGd-edjLy6Ai-s3KTKvW3t8vDefGUf8AFMcKaQxa7KlY42Ww2ng2ImRWJDf-riCT3XrPavDSV4KFUhSOCldwQ1wyhzEAMTjxBfOQl2kAySOHaTxVf"
    },
    json:{
        'to':'fP_p5FMcS0CtiRfd425emt:APA91bE9_x3ebse1CnCoz6560V_LMuYtUeaAc5N5H0WgknzVXGbULqb7GJ7bbF6KUx_751-FeTP0tuXDrO04tKMYKL0NXyMo_3sn-fV75mlYDwx6aTWqPU8Ose_iOk2pCr5FOJIYYDoN'
        ,'notification' : {
            'body' : '고객님, 내일 치과 예약 잊지 말아주세요!'
        }
    }
}
request.post(options, function(err, httpResponse, body){})