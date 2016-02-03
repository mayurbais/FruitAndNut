'use strict';

angular.module('try1App')
    .factory('SchoolDetails', function ($resource, DateUtils) {
        return $resource('api/schoolDetailss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.financialStartDate = DateUtils.convertLocaleDateFromServer(data.financialStartDate);
                    data.financialEndDate = DateUtils.convertLocaleDateFromServer(data.financialEndDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.financialStartDate = DateUtils.convertLocaleDateToServer(data.financialStartDate);
                    data.financialEndDate = DateUtils.convertLocaleDateToServer(data.financialEndDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.financialStartDate = DateUtils.convertLocaleDateToServer(data.financialStartDate);
                    data.financialEndDate = DateUtils.convertLocaleDateToServer(data.financialEndDate);
                    return angular.toJson(data);
                }
            }
        });
    });
