-- Populate AIRCRAFT table
INSERT INTO AIRCRAFT (AID, _Type_, Crew) VALUES ('IA001', 1, 3),
('IA002', 2, 5),
('IA003', 1, 4),
('IA004', 3, 6),
('IA005', 2, 5);

-- Populate AVIATOR table
INSERT INTO AVIATOR (Service_num, _Rank_, Flying_hours) VALUES 
('SN001', 3, 0),
('SN002', 4, 0),
('SN003', 2, 0),
('SN004', 5, 0),
('SN005', 3, 0);

-- Populate BASE table
INSERT INTO BASE (Location, Capacity, Holdings, Commander) VALUES 
('Ambala', 50, 1,'SN001'),
('Jaisalmer', 60, 1, 'SN002'),
('Yelahanka', 40, 1, 'SN003'),
('Tejpur', 55, 1, 'SN004'),
('Thanjavur', 45, 1, 'SN005');

-- Populate DEPLOYED_AT table
INSERT INTO DEPLOYED_AT (AID, Base) VALUES 
('IA001', 'Ambala'),
('IA002', 'Jaisalmer'),
('IA003', 'Yelahanka'),
('IA004', 'Thanjavur'),
('IA005', 'Tejpur');


