'use strict';

describe('Controller Tests', function() {

    describe('SchoolDetails Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSchoolDetails, MockUser, MockLanguageLOV, MockCurrancyLOV, MockCountryLOV;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSchoolDetails = jasmine.createSpy('MockSchoolDetails');
            MockUser = jasmine.createSpy('MockUser');
            MockLanguageLOV = jasmine.createSpy('MockLanguageLOV');
            MockCurrancyLOV = jasmine.createSpy('MockCurrancyLOV');
            MockCountryLOV = jasmine.createSpy('MockCountryLOV');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SchoolDetails': MockSchoolDetails,
                'User': MockUser,
                'LanguageLOV': MockLanguageLOV,
                'CurrancyLOV': MockCurrancyLOV,
                'CountryLOV': MockCountryLOV
            };
            createController = function() {
                $injector.get('$controller')("SchoolDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:schoolDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
