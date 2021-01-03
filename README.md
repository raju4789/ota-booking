# ota-booking

## Exposed end points:

### 1. POST:  http://localhost:8081/booking/v1/create: 

It takes as input the booking information (i.e. check-in date, hotel name,
number of guest and a booking reference) and creates a booking record in a database
and return the booking id.

### Sample Success Request:
{

    "checkInDate": "2021-02-07 07:08:01",
    "checkOutDate": "2021-02-11 15:08:01",
    "hotelName": "Paasha",
    "noOfGuests": 4

}

### Sample Response

{

    "httpStatus": 201,
    "bookingId": "8c9cc6fb-a398-44be-b33b-8963ca49b1db",
    "errors": null

}

### Sample Error Request:
{

    "checkInDate": "2021-02-07 07:08:01",
    "checkOutDate": "2021-02-11 15:08:01",
    "hotelName": "Paasha",
    "noOfGuests": 0

}

### Sample Response

{

    "httpStatus": 400,
    "bookingId": null,
    "errors": [
        "noOfGuests can't be less than 0"
    ]

}

### 2. GET:  http://localhost:8081/booking/v1/bookings?check_in_date={check_in_date}&check_out_date={check_out_date}&status={status}: 

It takes as input check-in and check-out dates and a status (which can be CONFIRMED or CANCELED) and returns all bookings matching the criteria. Each
booking information contains all data from that booking.


### Sample Request URL: http://localhost:8081/bookings?check_in_date=2021-05-10&status=confirmed

It takes as input check-in and check-out dates and a status (which can be CONFIRMED or CANCELED) and returns all bookings matching the criteria. Each
booking information contains all data from that booking.

### Sample Response.

{

    "httpStatus": 200,
    "bookings": [
        {
            "bookingId": "e1e050c4-be96-4c58-bc78-480449f0247e",
            "checkInDate": "2021-05-10 15:08:01",
            "checkOutDate": "2021-05-12 15:08:01",
            "hotelName": "Paasha",
            "noOfGuests": 4,
            "status": "CONFIRMED",
            "bookingReference": "1420a6fc-e402-46b1-8e8c-b7c9ce96c5d1",
            "createdOn": "2021-01-03 11:31:52",
            "updatedOn": "2021-01-03 11:31:52"
        }
    ],
    "errorMessage": null

}

### 3. POST:  http://localhost:8081/booking/v1/amend: 

It takes a booking id, check-in and check-out dates, a status (which can be CONFIRMED or CANCELED) and update the booking information in the database accordingly.

### Sample Request:
{

    "httpStatus": 200,
    "bookings": [
       {
        "checkInDate": "2021-01-19T15:08:00.630642",
        "checkOutDate": "2021-01-29T15:08:00.630642",
        "hotelName": "Paasha",
        "noOfGuests": 4
    },
    {
        "checkInDate": "2021-01-19T15:08:00.630642",
        "checkOutDate": "2021-01-29T15:08:00.630642",
        "hotelName": "Paasha",
        "noOfGuests": 4
    }
    ],
    "errorMessage": null

}

### 4. GET:  http://localhost:8081/booking/v1/filter?check_in_date={check_in_date}:

It takes as input date and return all bookings whose check-in-date greate than given date.

**NOTE:**

Above endpoint is added to synchronize bookings.

### Sample Request URL: http://localhost:8081/booking/v1/filter?check_in_date=2021-01-19

### Sample Response:

{

    "httpStatus": 200,
    "bookings": [
        {
            "bookingId": "31a577b6-a5fa-4d07-a62a-b14bef3ef67f",
            "checkInDate": "2021-02-09 15:08:01",
            "checkOutDate": "2021-02-15 15:08:01",
            "hotelName": "Paasha",
            "noOfGuests": 1,
            "status": "CONFIRMED",
            "bookingReference": "299d63f8-6110-48a8-8a10-60b08cf9eb3f",
            "createdOn": "2021-01-02 15:56:11",
            "updatedOn": "2021-01-02 15:56:11"
        }
    ],
    "errorMessage": null

}
