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
    var J = jQuery, _BATH = _MTONS.BASE_PATH;

    var _configs = {
        errorElement: "p",
        errorPlacement: function (error, element) {
            error.addClass("help-block");
            if ( element.prop( "name" ) === "email" ) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
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

        require.async(['validation', 'validation-additional'], function () {
            J(formId).validate(options);
        });
    };

    var Validate = {
        register: function (formId, sendCodeButtonId) {
            _bind_validate(formId, {
                rules: {
                    username: {
                        required: true,
                        check_username: true
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    code: {
                        required: true
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
                        required: '请输入用户名',
                        check_username: '只能是字母/字母+数字,不少于5位'
                    },
                    email: {
                        required: '请输入邮箱地址',
                        email: '邮箱格式不正确'
                    },
                    code: {
                        required: '请输入收到的验证码'
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

            J(sendCodeButtonId).click(function () {
                var btn = J(this).button('sending');
                var email = J('input[name=email]').val();
                J.getJSON(_BATH + '/email/send_code', {'email': email, 'type': 3}, function (data) {
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
                        required: '请输入用户名',
                        check_username: '只能是字母/字母+数字,不少于5位'
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
                    email: {
                        required: '请输入邮箱地址',
                        email: '邮箱格式不正确'
                    },
                    password: '请输入新密码',
                    code: '请输入收到的验证码',
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
                },
                messages: {
                    email: {
                        required: '请输入邮箱地址',
                        email: '邮箱格式不正确'
                    },
                    code: {
                        required: '请输入收到的验证码'
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
                    oldPassword: '请输入当前密码',
                    password: '请输入新密码',
                    password2: {
                        required: '请输入确认密码',
                        equalTo: '两次输入的密码不一致'
                    }
                }
            });
        },

        updateProfile: function (formId) {
            _bind_validate(formId, {
                rules: {
                    name: 'required'
                },
                messages: {
                    name: '请输入昵称'
                }
            });
        }
    };

    module.exports = Validate;
});