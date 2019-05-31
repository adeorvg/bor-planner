// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {

  apiEndpointAvailableCars: 'http://localhost:8082/api/freeCars',
  apiEndpointPlannedSchedules: 'http://localhost:8082/api/schedules',
  apiEndpointAllDrivers: 'http://localhost:8082/api/drivers',
  apiEnddpointAllCars: 'http://localhost:8082/api/cars',
  apiEndpointAllSchedules: 'http://localhost:8082/api/schedules',
  apiEndpointCancelSchedules: 'http://localhost:8082/api/schedules/delete',
  apiEndpointSaveSchedules: 'http://localhost:8082/api/schedules/save',
  apiEndpointAllVIPs : 'http://localhost:8082/api/VIPs',
  
  production: false
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
