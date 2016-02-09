'use strict';

describe('Controller Tests', function() {

    describe('YearlyDairyDescription Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYearlyDairyDescription, MockSchoolDetails;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYearlyDairyDescription = jasmine.createSpy('MockYearlyDairyDescription');
            MockSchoolDetails = jasmine.createSpy('MockSchoolDetails');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YearlyDairyDescription': MockYearlyDairyDescription,
                'SchoolDetails': MockSchoolDetails
            };
            createController = function() {
                $injector.get('$controller')("YearlyDairyDescriptionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:yearlyDairyDescriptionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
