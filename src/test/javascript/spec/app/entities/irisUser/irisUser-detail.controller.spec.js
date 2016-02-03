'use strict';

describe('Controller Tests', function() {

    describe('IrisUser Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockIrisUser, MockUser, MockLanguageLOV, MockCountryLOV, MockAddress;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockIrisUser = jasmine.createSpy('MockIrisUser');
            MockUser = jasmine.createSpy('MockUser');
            MockLanguageLOV = jasmine.createSpy('MockLanguageLOV');
            MockCountryLOV = jasmine.createSpy('MockCountryLOV');
            MockAddress = jasmine.createSpy('MockAddress');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'IrisUser': MockIrisUser,
                'User': MockUser,
                'LanguageLOV': MockLanguageLOV,
                'CountryLOV': MockCountryLOV,
                'Address': MockAddress
            };
            createController = function() {
                $injector.get('$controller')("IrisUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:irisUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
