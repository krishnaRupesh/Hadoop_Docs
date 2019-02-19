REGISTER Encrypt.jar;

A = LOAD '$mydata' using PigStorage(',') AS (PatientID: int, Name: chararray, DOB: chararray, PhoneNumber: chararray, EmailAddress: chararray, SSN: chararray, Gender: chararray, Disease: chararray, weight: float);

D = FOREACH A GENERATE PatientID, Name, DOB, DeIdentifyUDF(PhoneNumber,'12345678abcdefgh'), DeIdentifyUDF(EmailAddress,'12345678abcdefgh'),DeIdentifyUDF(SSN,'12345678abcdefgh'), DeIdentifyUDF(Disease,'12345678abcdefgh'), Gender,weight;

STORE D into 'PIGOUTPUT';