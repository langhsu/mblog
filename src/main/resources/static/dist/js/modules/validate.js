/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

define(function(require, exports, module) {
    require('validation');
    require('validation-additional');
    require('validation-localization');

    var J = jQuery, _BATH = _MTONS.BASE_PATH;

    var _configs = {
        errorElement: "em",
        errorPlacement: function (error, element) {
            error.addClass("help-block");
            error.insertAfter(element);
        },
        highlight: function (element, errorClass, validClass) {
            J(element).closest("div").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            J(element).closest("div").addClass("has-success").removeClass("has-error");
        }
    };

    var _bind_validate = function (formId, configs) {
        var options = J.extend({}, _configs, configs);
        J(formId).validate(options);
    };

    var Validate = {
        register: function (formId) {
            _bind_validate(formId, {
                rules: {
                    username: {
                        required: true,
                        check_username: true
                    },
                    password: {
                        required: true
                    },
                    password2: {
                        required: true,
                        equalTo: "#password"
                    }
                },
                messages: {
                    username: {
                        required: '请输入用户名'
                    },
                    password: {
                        required: '请输入密码'
                    },
                    password2: {
                        required: '请输入确认密码',
                        equalTo: '两次输入的密码不一致'
                    }
                }
            });
        },
        oauthRegister: function (formId) {
            _bind_validate(formId, {
                rules: {
                    username: {
                        required: true,
                        check_username: true
                    }
                },
                messages: {
                    username: {
                        required: '请输入用户名'
                    }
                }
            });
        },
        forgot: function (formId, sendCodeButtonId) {
            J(sendCodeButtonId).click(function () {
                var btn = J(this).button('sending');
                var email = J('input[name=email]').val();
                J.getJSON(_BATH + '/email/send_code', {'email': email, 'type': 2}, function (data) {
                    if (data.code === 0) {
                        btn.text('重新发送');
                        J('#message').html('<div class="alert alert-success">' + data.message + '</div>');
                    } else {
                        J('#message').html('<div class="alert alert-danger">' + data.message + '</div>');
                    }

                    btn.button('reset');
                });
            });

            _bind_validate(formId, {
                rules: {
                    email: {
                        required: true,
                        email: true
                    },
                    password: 'required',
                    code: 'required',
                    password2: {
                        required: true,
                        equalTo: "#password"
                    }
                },
                messages: {
                    password2: {
                        required: '请输入确认密码',
                        equalTo: '两次输入的密码不一致'
                    }
                }
            });
        },
        updateEmail: function (formId, sendCodeButtonId) {
            _bind_validate(formId, {
                rules: {
                    email: {
                        required: true,
                        email: true
                    },
                    code: {
                        required: true
                    }
                }
            });

            J(sendCodeButtonId).click(function () {
                var btn = J(this).button('sending');
                var email = J('input[name=email]').val();
                J.getJSON(_BATH + '/email/send_code', {'email': email, 'type': 1}, function (data) {
                    if (data.code === 0) {
                        btn.text('重新发送');
                        J('#message').html('<div class="alert alert-success">' + data.message + '</div>');
                    } else {
                        J('#message').html('<div class="alert alert-danger">' + data.message + '</div>');
                    }
                    btn.button('reset');
                });
            });
        },

        updatePassword: function (formId) {
            _bind_validate(formId, {
                rules: {
                    oldPassword: 'required',
                    password: 'required',
                    password2: {
                        required: true,
                        equalTo: "#password"
                    }
                },
                messages: {
                    password2: {
                        required: '请输入确认密码',
                        equalTo: '两次输入的密码不一致'
                    }
                }
            });
        },

        updateProfile: function (formId) {
            _bind_validate(formId, {});
        }
    };

    module.exports = Validate;
});