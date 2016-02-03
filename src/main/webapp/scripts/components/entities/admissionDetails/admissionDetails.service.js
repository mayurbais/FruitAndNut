'use strict';

angular.module('try1App')
    .factory('AdmissionDetails', function ($resource, DateUtils) {
        return $resource('api/admissionDetailss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.admissionDate = DateUtils.convertLocaleDateFromServer(data.admissionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.admissionDate = DateUtils.convertLocaleDateToServer(data.admissionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.admissionDate = DateUtils.convertLocaleDateToServer(data.admissionDate);
                    return angular.toJson(data);
                }
            },
            'newAdmissionDetailss':{
            	method: 'POST',
            	 transformRequest: function (data) {
                     data.admissionDate = DateUtils.convertLocaleDateToServer(data.admissionDate);
                     return angular.toJson(data);
                 }
            }
        });
    });
