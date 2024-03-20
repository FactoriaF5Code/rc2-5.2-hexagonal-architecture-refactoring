# TODO

-[ ] Open and close the cell gates
  -[ ] Only the non-dangerous dinosaurs cells can be open 
-[ ] Get car location (coordinates)
  -[ ] Coordinates come from an external (fake) API
-[ ] Start/Stop car
  -[ ] Uses the car repository to store the cars (id and type) but calls a fake API to start/stop
  -[ ] If the car has a malfunction (true) then it cannot start
-[ ] Sedate dinosaur
  -[ ] If the dinosaur is already sedated, it cannot be sedated again
  -[ ] Big dinosaurs need 2 dosages 