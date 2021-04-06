create table if not exists account
(
    id      uuid  default random_uuid() primary key,
    balance decimal( 20,2 ),
    user_id varchar2(255) not null unique
);

create table if not exists transaction
(
    id        uuid  default random_uuid() primary key,
    amount    decimal( 20,2 ),
    timeStamp timestamp( 9 ),
    reference varchar2( 255 ),
    account_id uuid,
    foreign key(account_id) references account(id) on DELETE cascade
)

