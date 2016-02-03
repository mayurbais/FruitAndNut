'use strict';

angular.module('try1App')
    .factory('Notice', function ($resource, DateUtils) {
        return $resource('api/notices/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.sendDate = DateUtils.convertDateTimeFromServer(data.sendDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
