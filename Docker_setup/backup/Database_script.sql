create table board_tab(
id varchar(255) primary key,
name varchar(255),
created_date TIMESTAMPTZ DEFAULT NOW());

create table board_member_tab(
id varchar(255) primary key,
fullname varchar(255),
username varchar(255),
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

create table board_list_tab(
id varchar(255) primary key,
name varchar(255),
board_id varchar(255),
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

create table card_tab(
id varchar(255) primary key,
name varchar(255),
board_list_id varchar(255),
total_checkitems int,
completed_checkitems int,
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

	
create table checklist_tab(
id varchar(255) primary key,
name varchar(255),
card_id varchar(255),
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

	
create table checkitem_tab(
id varchar(255) primary key,
name varchar(255),
checklist_id varchar(255),
status varchar(50),
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

create table checkitem_state_tab(
id varchar(255) primary key,
status varchar(50),
checklist_id varchar(255),
member_id varchar(255),
state_update_date TIMESTAMPTZ);

create table card_badges_tab(
card_id varchar(255),
checkitems int,
checkitems_checked int,
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

create table card_customfield_tab(
card_id varchar(255),
customfield_id varchar(255),
start_date TIMESTAMPTZ,
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);


create table c_board_tab(
id varchar(255),
name varchar(255),
parent_card_id varchar(255),
created_date TIMESTAMPTZ);

create table c_board_list_tab(
id varchar(255),
name varchar(255),
board_id varchar(255),
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

create table c_card_tab(
id varchar(255),
name varchar(255),
board_list_id varchar(255),
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);

create table c_card_movement_tab(
id varchar(255),
card_id varchar(255),
from_list_id varchar(255),
from_list_name varchar(255),
to_list_id varchar(255),
to_list_name varchar(255),
moved_date TIMESTAMPTZ,
created_date TIMESTAMPTZ DEFAULT NOW(),
last_update_date TIMESTAMPTZ);


INSERT INTO board_tab (id, name, created_date)
VALUES ('5bb51c0ba1607b7882da9083', 'SSP Motherboard', NOW());

ALTER TABLE checklist_tab
ADD CONSTRAINT fk_checklist
FOREIGN KEY (card_id)
REFERENCES card_tab (id)
ON DELETE CASCADE;  -

ALTER TABLE checkitem_tab
ADD CONSTRAINT fk_checkitem
FOREIGN KEY (checklist_id)
REFERENCES checklist_tab (id)
ON DELETE CASCADE;


 CREATE VIEW card_status_view as 
 SELECT c.id AS card_id,c.name AS card_name,c.board_list_id,itm.id AS checklist_item_id,itm.name AS checklist_item_name,
 itm.status,l.id AS checklist_id,l.name AS checklist_name
 FROM card_tab c,checklist_tab l,checkitem_tab itm
 WHERE c.id::text = l.card_id::text AND l.id::text = itm.checklist_id::text;


CREATE VIEW card_status_view as 
SELECT c.id as card_id,c.name as card_name,c.board_list_id,itm.id as checklist_item_id,itm.name as checklist_item_name,
itm.status, l.id as checklist_id, l.name as checklist_name
FROM card_tab c, checklist_tab l, checkitem_Tab itm
where c.id=l.card_id
and l.id=itm.checklist_id;

CREATE VIEW  card_readiness_view as
SELECT c.id AS card_id,c.name AS card_name,c.board_list_id,c.completed_checkitems,c.total_checkitems,l.id AS checklist_id,l.name AS checklist_name,bl.name AS board_list_name
FROM card_tab c,checklist_tab l,board_list_tab bl
WHERE c.id::text = l.card_id::text AND bl.id::text = c.board_list_id::text
ORDER BY c.name;

CREATE VIEW  card_readiness_view as 
SELECT c.id as card_id,c.name as card_name,c.board_list_id,c.completed_checkitems,c.total_checkitems, l.id as checklist_id,l.name as checklist_name, bl.name as board_list_name
FROM card_tab c, checklist_tab l, board_list_tab bl
where c.id=l.card_id
and bl.id=c.board_list_id
order by c.name;

 CREATE VIEW card_checklist_view as 
 SELECT bl.name AS board_list_name,c.name AS card_name,l.name AS checklist_name,itm.name AS checklist_item_name,itm.status
 FROM card_tab c,checklist_tab l,checkitem_tab itm,board_list_tab bl
 WHERE c.id::text = l.card_id::text AND l.id::text = itm.checklist_id::text AND bl.id::text = c.board_list_id::text;

CREATE VIEW card_checklist_view as 
SELECT bl.name as board_list_name,c.name as card_name,l.name as checklist_name, itm.name as checklist_item_name,
itm.status 
FROM card_tab c, checklist_tab l, checkitem_Tab itm,  board_list_tab bl
where c.id=l.card_id
and l.id=itm.checklist_id
and bl.id=c.board_list_id; 

 CREATE VIEW card_completion_view as 
 SELECT c.join_date AS statedate,cistate.status,itm.name AS checkitem_name,cl.name AS checklist_name,c.name AS card_name,bl.name AS list_name,
 0 AS completed_card,0 AS completed_checklist
  FROM checkitem_state_tab cistate,checkitem_tab itm,checklist_tab cl,card_tab c,board_list_tab bl
  WHERE cistate.id::text = itm.id::text AND cistate.status::text = 'complete'::text AND itm.checklist_id::text = cl.id::text AND cl.card_id::text = c.id::text AND c.board_list_id::text = bl.id::text
UNION
 SELECT cistate.state_update_date AS statedate,cistate.status,itm.name AS checkitem_name,cl.name AS checklist_name,
    c.name AS card_name,bl.name AS list_name,
    count(c.name) OVER (PARTITION BY c.name ORDER BY cistate.state_update_date) AS completed_card,
    count(c.name) OVER (PARTITION BY c.name, cl.name ORDER BY cl.name, cistate.state_update_date) AS completed_checklist
   FROM checkitem_state_tab cistate,checkitem_tab itm,checklist_tab cl,card_tab c,board_list_tab bl
  WHERE cistate.id::text = itm.id::text AND cistate.status::text = 'complete'::text AND itm.checklist_id::text = cl.id::text AND cl.card_id::text = c.id::text AND c.board_list_id::text = bl.id::text;

CREATE VIEW card_completion_view as 
select cistate.state_update_date as statedate, cistate.state as status, itm.name as checkitem_name,
cl.name as checklist_name, c.name as card_name, bl.name as list_name
from checkitem_state_tab as cistate, checkitem_tab as itm,checklist_tab as cl, 
card_tab as c, board_list_tab as bl
where cistate.id = itm.id
and cistate.state='complete'
and itm.checklist_id = cl.id
and cl.card_id = c.id
and c.board_list_id = bl.id;


#Not in use
CREATE VIEW card_time_view as 
 SELECT cistate.state_update_date AS statedate,cistate.state AS status,itm.name AS checkitemname,cl.name AS checklistname,c.name AS cardname,bl.name AS listname
   FROM checkitem_state_tab cistate,checkitem_tab itm,checklist_tab cl,card_tab c,board_list_tab bl
  WHERE cistate.id::text = itm.id::text AND cistate.state::text = 'complete'::text AND itm.checklist_id::text = cl.id::text AND cl.card_id::text = c.id::text AND c.board_list_id::text = bl.id::text;

CREATE VIEW ccard_movement_view as 
SELECT 
	cb.name as company_name,
	ccard.name as card_name,
    COUNT(ccard.id) AS total_moved,
    from_list_name,
    to_list_name,
	MIN(moved_date) AS date_moved
FROM public.c_board_tab as cb
inner join c_board_list_tab as cblist on cb.id=cblist.board_id
inner join c_card_tab as ccard on ccard.board_list_id = cblist.id 
inner join c_card_movement_tab as cmove on cmove.card_id=ccard.id
GROUP BY 
	ccard.name,
    cb.name,
	from_list_name, to_list_name;


CREATE VIEW  ccard_readiness_view as 
SELECT cb.name as company_name,ccard.name as card_name,cblist.name as list_name,cmove.from_list_name as from_list_name, cmove.to_list_name as to_list_name,cmove.moved_date as moved_date
FROM c_board_tab cb,c_card_tab ccard, c_board_list_tab  cblist, c_card_movement_tab cmove
where cb.id = cblist.board_id  and ccard.board_list_id = cblist.id  and cmove.card_id=ccard.id
order by ccard.name;

SELECT cb.name AS company_name,
    ccard.name AS card_name,
    cblist.name AS list_name,
    cmove.from_list_name,
    cmove.to_list_name,
    cmove.moved_date
   FROM c_board_tab cb,
    c_card_tab ccard,
    c_board_list_tab cblist,
    c_card_movement_tab cmove
  WHERE cb.id::text = cblist.board_id::text AND ccard.board_list_id::text = cblist.id::text AND cmove.card_id::text = ccard.id::text
  ORDER BY ccard.name;
  

