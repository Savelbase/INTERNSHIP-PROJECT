># **Steps for invoking Registration endpoints**

* ## Bank client registration
    * ### **Generate access token if user doesn't registered yet**
      * ### POST /api/v1/sign-up/client/phone
    * ### **Create verification code**
      * ### POST /api/v1/sign-up/client/create/code
    * ### **Check verification code** 
      * ### POST /api/v1/sign-up/client/check/code
    * ### **Save password** 
      * ### PATCH /api/v1/sign-up/client/password
    * ### **Accept policy and Complete registration** 
      * ### PATCH /api/v1/sign-up/client/{}/acceptPolicy

* ## Non bank resident client registration
    * ### **Generate access token if user doesn't registered yet**
      * ### POST /api/v1/sign-up/client/phone
    * ### **Create verification code**
      * ### POST /api/v1/sign-up/client/create/code
    * ### **Check verification code**
      * ### POST /api/v1/sign-up/client/check/code
    * ### **Save password**
      * ### PATCH /api/v1/sign-up/client/password
    * ### **Save resident passport number**
      * ### PATCH /api/v1/sign-up/client/{}/passport
    * ### **Save resident full name**
      * ### PATCH /api/v1/sign-up/client/{}/fullName
    * ### **Save security question and answer**
      * ### PATCH /api/v1/sign-up/client/{}/security/question/answer
    * ### **Accept policy and Complete registration**
      * ### PATCH /api/v1/sign-up/client/{}/acceptPolicy
