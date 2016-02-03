'use strict';

describe('Controller Tests', function() {

    describe('Section Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSection, MockCourse, MockTeacher, MockRoom, MockTimeTable;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSection = jasmine.createSpy('MockSection');
            MockCourse = jasmine.createSpy('MockCourse');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockRoom = jasmine.createSpy('MockRoom');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Section': MockSection,
                'Course': MockCourse,
                'Teacher': MockTeacher,
                'Room': MockRoom,
                'TimeTable': MockTimeTable
            };
            createController = function() {
                $injector.get('$controller')("SectionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:sectionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
