'use strict';

angular.module('try1App')
    .factory('AcedemicSession', function ($resource, DateUtils) {
        return $resource('api/acedemicSessions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.sessionStartDate = DateUtils.convertDateTimeFromServer(data.sessionStartDate);
                    data.sessionEndDate = DateUtils.convertDateTimeFromServer(data.sessionEndDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
